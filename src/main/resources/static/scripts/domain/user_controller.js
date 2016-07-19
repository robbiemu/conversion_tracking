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
			if($routeParams.id > 0){
				$http.post(SPRING_INCREMENTANON_URI, $routeParams.id)
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