angular.module(MODULE_NAME).controller('UserController', 
    [ '$routeParams', '$scope', '$http', '$location', 'Auth', function ($routeParams, $scope, $http, $location, Auth) {
    	    	
		  $scope.navTo = function(url) {
			    if ($location.path() === url) {
			      $route.reload()
			    } else {
			      $location.path(url)
			    }
			}
		  
		$scope.$on('$viewContentLoaded', function() {
			if($routeParams.extension > 0){
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
			switch($location.path()){
			case "/login":
				href = "styles/login.css"
//		  	break
//			case "/admin":
//				href = "styles/admin.css"
			} 
			if(href){
				console.log("styling page with " + href)
		  		$scope._style = document.createElement('link')
		  		$scope._style.type = 'text/css'
		  		$scope._style.href = href
		  		$scope._style.rel = 'stylesheet'
		
		  		$scope._style = document.head.appendChild($scope._style)
		
		  		$scope.$on('$destroy', function() {
		  			$scope._style.parentNode.removeChild($scope._style)
		  			delete $scope._style
		  		})		
			}  
		})
		
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