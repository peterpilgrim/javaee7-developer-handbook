var newproject = angular.module('newproject', ['ui.bootstrap.modal', 'sharedService'])

newproject.controller('NewProjectModalController', function($scope, $modal, $http, $log, sharedService ) {
    $log.info("Project Modal Dialog. Yes!  $modal="+$modal+", $log="+$log);

    $scope.selected = false;
    $scope.project = {
        name: '', headline: '', description: ''
    };
    $scope.returnedData = null;

    $scope.open = function () {

        var modalInstance = $modal.open({
            templateUrl: 'newProjectContent.html',
            controller: newProjectModalInstanceController,
            resolve: {
                project: function () {
                    return $scope.project;
                }
            }
        });

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            $http.post('rest/projects/item', $scope.project).success(function(data) {
                $log.info("data="+data);
                $scope.returnedData = data;
                sharedService.setBroadcastMessage("newproject");
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.openEditProjectDialog = function (projectItem) {
        // Deep copy of the project!
        $scope.project = {
            id: projectItem.id, name: projectItem.name,
            headline: projectItem.headline, description: projectItem.description
        };
        var modalInstance = $modal.open({
            templateUrl: 'editProjectContent.html',
            controller: editProjectModalInstanceController,
            resolve: {
                project: function () {
                    return $scope.project;
                }
            }
        });

        modalInstance.result.then(function (data) {
            $scope.selected = data;
            $http.put('rest/projects/item/'+$scope.project.id, $scope.project).success(function(data) {
                $log.info("data="+data);
                $scope.returnedData = data;
                sharedService.setBroadcastMessage("editproject");
            });

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

});

var newProjectModalInstanceController = function ($scope, $modalInstance, project) {
    $scope.project = project;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};


var editProjectModalInstanceController = function ($scope, $modalInstance, project) {
    $scope.project = project;

    $scope.ok = function () {
        $modalInstance.close(true);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};

