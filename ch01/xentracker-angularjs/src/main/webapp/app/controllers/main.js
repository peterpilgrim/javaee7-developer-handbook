var myApp = angular.module('app', ['ui.bootstrap', 'masterview','newproject','newtask'])
    .controller('ProjectController', function ($scope, $http ) {
        var self = this;
        $scope.projects = [
            {name: "Digital Moves"},
            {name: "Counsellor"},
            {name: "Offspring"}
        ];

        $scope.getProjects = function () {
            $http.get('rest/projects/list').success(function(data) {
                console.log("data="+data);
                $scope.projects = data;
            });
        }

        $scope.$on('handleBroadcastMessage', function() {
            $scope.getProjects();
        })

        // Retrieve the initial list of projects
        $scope.getProjects();


    });

angular.module('masterview', [])
    .controller('car', function ($log) {
        return {
            foo: function() {
                $log.info('Call the foo() method');
            }
        };

    });

