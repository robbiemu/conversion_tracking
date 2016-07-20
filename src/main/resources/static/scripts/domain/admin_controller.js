angular.module(MODULE_NAME).controller('AdminController', 
		['$scope', '$location', '$http', function ($scope, $location, $http) {
			$scope.$on('$viewContentLoaded', function() {
				let href = false
				console.log("home controller")
				console.log("considering css for " + $location.path())
				switch($location.path()){
//				case "/login":
//					href = "styles/login.css"
//			  	break
				case "/admin":
					href = "styles/admin.css"
				} 
				if(href){
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
			
			var ctrl = this;
			ctrl.path = $location.path()
			
			$scope.new_url = function () {
				let url = $scope.url
				url.baseURL = '/login'
				url.extensionURL = getExtensionURL(url.baseURL)
			    $http.post(SPRING_NEWURL_URI, url).then((tx_response) => {
			    	if(tx_response.status == 200){
			    		// success
			    		let url = tx_response.data
			    		url.anonymousCount = 0
			    		$scope.URLs.push(url)
			    	} else {
			    		// failure
			    	}
			    })
			}
			
			$scope.delete = function (label) {
/*				tx_short = { '"label"': label }
				params = {
						  method: 'DELETE',
						  url: SPRING_DELETEBYLABELURL_URI,
						  data: JSON.stringify(tx_short),
						  headers: '{"Content-Type": "application/json;charset=utf-8"}'
				}
				$http(params).then((tx_response) => { */
				$http.get(SPRING_DELETEBYLABELURL_URI + "/" + label).then((tx_response) => {
					if(tx_response.status == 200) {
						$scope.URLs = $scope.URLs.filter((x) => x.label !== label)
					}					
				})
			}
			
			const getExtensionURL = function (baseURL) {
				let max = 0
				let extensions = $scope.URLs.filter((x) => x.baseURL === baseURL).map((x) => x.extensionURL)
				do {
					max++
				} while(extensions.indexOf(max) != -1)
				return max
			}
		}]
);