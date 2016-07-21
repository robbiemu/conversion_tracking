angular.module(MODULE_NAME).controller('AdminController', 
		['$scope', '$location', '$http', 'Res',  function ($scope, $location, $http, Res) {
			var ctrl = this;
			ctrl.path = $location.path()
			
			$scope.$on('$viewContentLoaded', function() {
				let href = false
				console.log("adminController")
				console.log("considering css for " + $location.path())
				if($location.path() ===  "/admin"){
					Res.style(['scripts/node_modules/n3-charts/build/LineChart.css', 'scripts/node_modules/jquery-confirm/css/jquery-confirm.css', 'styles/admin.css'])
					Res.script(['scripts/node_modules/n3-charts/build/LineChart.js', 'scripts/node_modules/jquery-confirm/js/jquery-confirm.js', 'scripts/admin.js'])
				}
			})
	
			$scope.states = ['All Time', 'Weekly', 'Monthly', 'Yearly']
			$scope.selection = 'All Time'
			$scope.previous_selection = 'All Time'

			const reloadURLs = function(url, degree) {
				$http.get(url + '/' + degree).then((tx_response) => {
					if(tx_response.status == 200) {
						$scope.URLs = []
						for(let e in tx_response.data.field){
							let url = tx_response.data.field[e].right
							url.anonymousCount = tx_response.data.field[e].left[0]
							url.conversions = tx_response.data.field[e].left[1]
							url.conversionRate = tx_response.data.field[e].left[2]
							
							$http.get('/url/tracking/' + degree).then((r) => {
								if(r.status == 200){
									console.dir(r.data)									
								}
							})

							$scope.URLs.push(url)
						}
					}
				})									
			}
						
			$scope.prorate = function() {
				if(STATES[this.selection]) {
					reloadURLs(SPRING_TRACKING_DETAILS_URI, STATES[this.selection])
				} else if($scope.previous_selection !== $scope.selection) {
					reloadURLs(SPRING_TRACKING_DETAILS_URI)
				}
				$scope.previous_selection = $scope.selection
			}
			
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
			    		decorateURLs()
			    	} else {
			    		// failure
			    	}
			    })
			}
			
			$scope.delete = function (label) {
		       	$.confirm({
		       		title: 'Delete the label?',
		       		content: 'Are you sure you want to delete the label?',
		       		confirm: function(){
						$http.get(SPRING_DELETEBYLABELURL_URI + "/" + label).then((tx_response) => {
							if(tx_response.status == 200) {
								$scope.URLs = $scope.URLs.filter((x) => x.label !== label)
							}					
						})
					}
		       	})

/*				tx_short = { '"label"': label }
				params = {
						  method: 'DELETE',
						  url: SPRING_DELETEBYLABELURL_URI,
						  data: JSON.stringify(tx_short),
						  headers: '{"Content-Type": "application/json;charset=utf-8"}'
				}
				$http(params).then((tx_response) => { */

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
)