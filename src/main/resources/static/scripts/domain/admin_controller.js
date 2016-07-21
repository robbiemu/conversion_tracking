angular.module(MODULE_NAME).controller('AdminController', 
		['$scope', '$location', '$http', function ($scope, $location, $http) {
			$scope.$on('$viewContentLoaded', function() {
				let href = false
				console.log("adminController")
				console.log("considering css for " + $location.path())
				if($location.path() ===  "/admin"){
					style('styles/admin.css')
					script(['scripts/node_modules/bootstrap-select/js/bootstrap-select.js', 'scripts/node_modules/jquery-confirm/js/jquery-confirm.js', 'scripts/admin.js'])
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
	
			$scope.states = ['All Time', 'Weekly', 'Monthly', 'Yearly']
			$scope.selection = 'All Time'
			$scope.previous_selection = 'All Time'

			const reloadURLs = function(url) {
				$http.get(url).then((tx_response) => {
					if(tx_response.status == 200) {
						$scope.URLs = []
						for(let e in tx_response.data.field){
							let url = tx_response.data.field[e].right
							url.anonymousCount = tx_response.data.field[e].left[0]
							url.conversions = tx_response.data.field[e].left[1]
							url.conversionRate = tx_response.data.field[e].left[2]
							$scope.URLs.push(url)
						}
					}
				})									
			}
			
			$scope.prorate = function() {
				if(STATES[this.selection]) {
					reloadURLs(SPRING_TRACKING_DETAILS_URI + "/" + STATES[this.selection])
				} else if($scope.previous_selection !== $scope.selection) {
					reloadURLs(SPRING_TRACKING_DETAILS_URI)
				}
				$scope.previous_selection = $scope.selection
			}
			
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