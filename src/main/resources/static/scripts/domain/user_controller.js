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
				style("styles/login.css")
			}
			if(/^\/register/.test($location.path())){
				style("styles/register.css")
			}
		})
		
			const style = function(inp) {
				let hrefs = []
				if(typeof inp === 'string') {
					hrefs.push(inp)
				} else {
					hrefs = inp
				}
				$scope._styles=[]
				for(let i in hrefs){
					console.log("styling page with " + hrefs[i])
			  		let style = document.createElement('link')
			  		style.type = 'text/css'
			  		style.href = hrefs[i]
			  		style.rel = 'stylesheet'
			
					if($scope._rawStyles === undefined) {
						$scope._rawStyles = []
					}
					$scope._rawStyles.push(script)
			  		$scope._styles.push( document.head.appendChild(style) )
			
					$scope.$on('$destroy', function() {
						for (let key in $scope._styles){
							$scope._styles[key].parentNode.removeChild($scope._rawStyles[key])
							delete $scope._styles[key]
							delete $scope._rawStyles[key]
						}
					})			
				}
			}
		
			const script = function(inp) {
				let hrefs = []
				if(typeof inp === 'string') {
					hrefs.push(inp)
				} else {
					hrefs = inp
				}
				$scope._scripts=[]
				for(let i in hrefs){
					console.log("loading javascript: " + hrefs[i])
					let script = document.createElement('script')
					script.type = 'text/javascript'
					script.src = hrefs[i]
				
					if($scope._rawScripts === undefined) {
						$scope._rawScripts = []
					}
					$scope._rawScripts.push(script)
					$scope._scripts.push( document.head.appendChild(script) )
					
					$scope.$on('$destroy', function() {
						for (let key in $scope._scripts){
							$scope._scripts[key].parentNode.removeChild($scope._rawScripts[key])
							delete $scope._scripts[key]
							delete $scope._rawScripts[key]
						}
					})							
				}
			}

		
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