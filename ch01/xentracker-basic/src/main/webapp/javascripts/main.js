var myApp = angular.module('project', []);

function ProjectController($scope, $http) {

    var self = this;
    $scope.projects = [
        {name: "Digital Moves"},
        {name: "Counsellor"},
        {name: "Offspring"}
    ];

    this.addProject = function () {
    };

    this.addTaskToProject = function () {
    };

    this.getProjects = function () {
        $http.get('rest/projects/list').success(function(data) {
            console.log("data="+data);
            $scope.projectsAjax = data;
            $scope.projects = data;
        });
    }

    this.getProjects();
}

// ProjectController.$inject = ['$scope', '$http'];