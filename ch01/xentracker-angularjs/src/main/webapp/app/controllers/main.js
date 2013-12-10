var myApp = angular.module('app', ['ui.bootstrap', 'masterview','newproject','newtask']);

myApp.factory('UpdateTaskStatusFactory', function( $log ) {
    var service = {};

    service.connect = function() {
        if (service.ws) { return; }

        var ws = new WebSocket("ws://localhost:8080/xentracker/update-task-status");

        ws.onopen = function() {
            console.log("WebSocket connect was opened");
        };

        ws.onclose = function() {
            console.log("WebSocket connection was closed");
        }

        ws.onerror = function() {
            console.log("WebSocket connection failure");
        }

        ws.onmessage = function(message) {
            console.log("message received ["+message+"]");
        };

        service.ws = ws;
    }

    service.send = function(message) {
        service.ws.send(message);
        service.ws.send(message);
    }

    service.subscribe = function(callback) {
        service.callback = callback;
    }

    return service;
});



myApp.controller('ProjectController', function ($scope, $http, $log, UpdateTaskStatusFactory ) {
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

//    UpdateTaskStatusFactory.subscribe(function(message) {
//        $scope.messages.push(message);
//        $scope.$apply();
//    });

    $scope.connect = function() {
        UpdateTaskStatusFactory.connect();
    }

    $scope.send = function( msg ) {
        UpdateTaskStatusFactory.send(msg);
    }

    $scope.updateProjectTaskCompleted = function( task ) {
        var message = { 'projectId': task.projectId, 'taskId': task.id, 'completed': task.completed }
        $scope.connect()
        var jsonMessage = JSON.stringify(message)
        console.log("jsonMessage = "+jsonMessage)
        $scope.send(jsonMessage)
    }
});

angular.module('masterview', [])
    .controller('car', function ($log) {
        return {
            foo: function() {
                console.log('Call the foo() method');
            }
        };

    });

