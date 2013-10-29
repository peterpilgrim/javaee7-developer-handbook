var newtask = angular.module('newtask', ['ui.bootstrap.modal', 'sharedService'])
newtask.config(function($httpProvider) {
    /**
     * make delete type json
     */
    $httpProvider.defaults.headers["delete"] = {
        'Content-Type': 'application/json;charset=utf-8'
    };
})
newtask.controller('NewTaskModalController', function($scope, $modal, $http, $log, sharedService ) {
    $log.info("Modal Task Dialog!!!!  $modal="+$modal+", $log="+$log+", sharedService="+sharedService);

    $scope.selected = false;
    $scope.task = {
        id: 0, name: '', targetDate: null, completed: false, projectId: 0
    };
    $scope.returnedData = null;

    $scope.openNewTaskDialog = function(projectId) {

        var modalInstance = $modal.open({
            templateUrl: 'newTaskContent.html',
            controller: newTaskModalInstanceController,
            resolve: {
                task: function () {
                    return $scope.task;
                }
            }
        });

        $log.info('>>>> projectId='+projectId);

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            $http.post('rest/projects/item/'+projectId+'/task', $scope.task).success(function(data) {
                $log.info("data="+data);
                $scope.returnedData = data;
                sharedService.setBroadcastMessage("newTask");
                // Reset Task in this scope for better UX affordance.
                $scope.task = {
                    id: 0, name: '', targetDate: null, completed: false, projectId: 0
                };
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.openEditTaskDialog = function(taskItem) {
        // Deep copy the task object, so that original remains unchanged
        $scope.task = {
            id: taskItem.id, name: taskItem.name,
            targetDate: taskItem.targetDate, completed: taskItem.completed,
            projectId: taskItem.projectId
        };

        var modalInstance = $modal.open({
            templateUrl: 'editTaskContent.html',
            controller: editTaskModalInstanceController,
            resolve: {
                task: function () {
                    return $scope.task;
                }
            }
        });

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            $http.put('rest/projects/item/'+$scope.task.projectId+'/task/'+$scope.task.id, $scope.task).success(function(data) {
                $log.info("data="+data);
                $scope.returnedData = data;
                sharedService.setBroadcastMessage("editTask");
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.openDeleteTaskDialog = function(taskItem) {
        $scope.task = taskItem;

        var modalInstance = $modal.open({
            templateUrl: 'deleteTaskContent.html',
            controller: deleteTaskModalInstanceController,
            resolve: {
                task: function () {
                    return taskItem;
                }
            }
        });

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            // generate a 415 Unsupported Media Type
            // Using a SIDE EFFECT GET instead! ;-(
            $http.delete('rest/projects/item/'+taskItem.projectId+'/task/'+taskItem.id).success(function (data) {
//            $http.get('rest/projects/item/'+taskItem.projectId+'/task/'+taskItem.id+'/delete', taskItem).success(function(data) {
                $log.info("data="+data);
                $scope.returnedData = data;
                sharedService.setBroadcastMessage("deleteTask");
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});

var newTaskModalInstanceController = function ($scope, $modalInstance, task) {
    $scope.task = task;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};

var editTaskModalInstanceController = function ($scope, $modalInstance, task) {
    $scope.task = task;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};

var deleteTaskModalInstanceController = function ($scope, $modalInstance, task) {
    $scope.task = task;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};
