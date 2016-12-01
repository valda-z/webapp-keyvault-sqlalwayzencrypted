'use strict';

angular.module('myApp.neteth0', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/neteth0', {
            templateUrl: 'views/neteth0.html',
            controller: 'NetETH0Ctrl'
        });
    }])

    .controller('NetETH0Ctrl', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

        $scope.saveInfo = {
            showLabel :false,
            disableSave : false
        };

        $scope.loadData = function () {
            $http.get( '/api/netmanager/eth0' ).success( function ( data ) {
                $scope.item = data;
                _validatorETH0OnLoad();
            });
        };

        // event handler for save data
        $scope.saveSettings = function (item) {
            if(item.dhcp){
                item.address="";
                item.mac="";
                item.gateway="";
                item.dns="";
            }else{
                if(!_isvalidETH0()){
                    return;
                }
            }
            $scope.saveInfo.disableSave = true;
            myUtils.showPleaseWait();
            $http.post('/api/netmanager/eth0/set', item).success(function(data){
                $scope.saveInfo.showLabel = true;
            }).finally(function(data){
                $scope.saveInfo.disableSave = false;
                myUtils.hidePleaseWait();
            });
        };

        var refreshData = function() {
            $http.get( '/api/netmanager' ).success( function ( data ) {
                $scope.adapter = data.eth0;
            }).finally( function() {
                promise = $timeout(refreshData, 1000);
            });
        };

        var promise = $timeout(refreshData, 500);

        $scope.loadData();

        // Cancel interval on page changes
        $scope.$on('$destroy', function(){
            if (angular.isDefined(promise)) {
                $timeout.cancel(promise);
                promise = undefined;
            }
        });

    }]);

