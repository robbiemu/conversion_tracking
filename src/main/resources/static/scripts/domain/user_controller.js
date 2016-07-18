angular.module(MODULE_NAME).controller('UserController', 
    [ '$scope', '$http', '$location', 'Auth', function ($scope, $http, $location, Auth) {
    	    	
  	  $scope.navTo = function(url) {
		    if ($location.path() === url) {
		      $route.reload();
		    } else {
		      $location.path(url);
		    }
		}

    	$scope.login = function () {
            $http.post(SPRING_LOGIN_URI, $scope.user).then((tx_response) => {
            	if((tx_response.data.fieldType === "Login") && tx_response.data.field["Logged In"]){
                	Auth.setUser($scope.user)
                	$location.path(tx_response.data.field["Admin account"]? "/admin": "/")
            	}
            })
        }
    }]
)