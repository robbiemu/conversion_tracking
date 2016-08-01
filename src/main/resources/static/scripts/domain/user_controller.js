angular.module(MODULE_NAME).controller('UserController', 
    [ '$routeParams', '$scope', '$http', '$location', 'Auth', 'Res', function ($routeParams, $scope, $http, $location, Auth, Res) {

		  $scope.navTo = function(url) {
			    if ($location.path() === url) {
			      $route.reload()
			    } else {
			      $location.path(url)
			    }
			}
		  
		$scope.$on('$viewContentLoaded', function() {
			if(($routeParams.extension > 0) && (/^\/login/.test($location.path())) ){
				$scope.extensionURL = '/' + $routeParams.extension
				$http.post(SPRING_FINDURL_URI, { baseURL: (/(.*)\/[^\/]/.exec($location.path()))[1], extensionURL: $routeParams.extension } )
					.then((tx_response) => {
						if(tx_response.status == 200) {
							$http.post(SPRING_INCREMENT_ANONYMOUS_URI,  { label: tx_response.data.label})
						}
					})
			}
			let href = false
			console.log("userController")
			console.log("considering css for " + $location.path())
			if(/^\/login/.test($location.path())){
				Res.style("styles/login.css")
			}
			if(/^\/register/.test($location.path())){
				Res.style("styles/register.css")
			}
		})
		
		$scope.login = function () {
		    $http.post(SPRING_LOGIN_URI, $scope.user).then((tx_response) => {
		    	if((tx_response.data.fieldType === "Login") && tx_response.data.field["Logged In"]){
		    		$scope.user.isAdmin = tx_response.data.field["Admin account"]? true: false
		    		Auth.setUser($scope.user)
		        	$http.post(SPRING_FINDURL_URI, { baseURL: '/login', extensionURL: $routeParams.extension } )
						.then((inner_tx_response) => {
							if(inner_tx_response.status == 200 && inner_tx_response.data.label !== undefined) {
								$http.post(SPRING_DECREMENT_ANONYMOUS_URI,  { label: inner_tx_response.data.label})
							}
				        	$location.path(tx_response.data.field["Admin account"]? "/admin": "/")
						})
		    	}
		    })
		}
		
		$scope.register = function () { // for the moment, the assumption is /register/:extension calls are coming from /login/:extension
			user = $scope.user
			user.num=1
			user.label=$scope.label
			console.dir(user)
		    $http.post(SPRING_REGISTER_URI, user).then((tx_response) => {
		    	if (tx_response.status == 200) {
		        	Auth.setUser($scope.user)
		        	$location.path("/")
		    	}
		    })
		}
    }]
)