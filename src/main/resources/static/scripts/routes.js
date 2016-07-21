//require('./constants')

angular.module(MODULE_NAME)
    .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/', {
                templateUrl: HOME_PAGE,
                controller: 'HomeController',
                controllerAs: 'homeController',
                resolve: {
                    factory: function ($q, $rootScope, $location, $http, Auth, Res) {
                		Res.clean_scripts()
                		Res.clean_styles()
                		
                    	let checkAdmin = false
                    	checkRouting($q, $rootScope, $location, Auth, checkAdmin)
                    }
                }
            })
            .when('/admin', {
                templateUrl: ADMIN_PAGE,
                controller: 'AdminController',
                controllerAs: 'adminController',
                resolve: {
                	factory: function ($q, $rootScope, $location, $http, Auth, Res) {
                		Res.clean_scripts()
                		Res.clean_styles()

                		loadTrackingDetails($rootScope, $http)
                    	let checkAdmin = true
                    	checkRouting($q, $rootScope, $location, Auth, checkAdmin)
                    }
                }
            })
            .when('/login', {
                templateUrl: LOGIN_PAGE,
                controller: 'UserController',
                controllerAs: 'userController',
                resolve: {
                	factory: function (Res) { 
                		Res.clean_scripts()
                		Res.clean_styles()
                	}
                }
            })
            .when('/register', {
                templateUrl: REGISTER_PAGE,
                controller: 'UserController',
                controllerAs: 'userController',
            	resolve: {
                	factory: function (Res) { 
                		Res.clean_scripts()
                		Res.clean_styles()
                	}
            	}
            })
            .when('/register/:extension', {
                templateUrl: REGISTER_PAGE,
                controller: 'UserController',
                controllerAs: 'userController',
                resolve: {
                	factory: function ($rootScope, $location, $http, Res) { 
                		Res.clean_scripts()
                		Res.clean_styles()
                		
                		get_url_details($rootScope, $location, $http)
                	}
                }
            })
           .when('/login/:extension', {
                templateUrl: LOGIN_PAGE,
                controller: 'UserController',
                controllerAs: 'userController',
                resolve: {
                	factory: function (Res) { 
                		Res.clean_scripts()
                		Res.clean_styles()
                	}
                }
            })
            .otherwise('/')
        }
   ])

 const get_url_details = function($rootScope, $location, $http) {
	let extension = ($location.path().match(/\/([^\/])+$/))[1]
	
	$http.post(SPRING_FINDURL_URI, { baseURL: '/login', extensionURL: extension }).then((tx_response) => {
		if(tx_response.status == 200) {
			$rootScope.label = tx_response.data.label
			$rootScope.description = tx_response.data.description
		}
	}) 
}
   
const loadTrackingDetails = function ($rootScope, $http) {
	$http.get(SPRING_TRACKING_DETAILS_URI).then((tx_response) => {
		if(tx_response.status == 200) {
			$rootScope.URLs = []
			for(let e in tx_response.data.field){
				let url = tx_response.data.field[e].right
				url.anonymousCount = tx_response.data.field[e].left[0]
				url.conversions = tx_response.data.field[e].left[1]
				url.conversionRate = tx_response.data.field[e].left[2]
				$rootScope.URLs.push(url)
			}
		}
	})
}
   
const checkRouting = function ($q, $rootScope, $location, Auth, checkAdmin=false) {
	let pass = true;
	if (!Auth.isLoggedIn() || (checkAdmin && !Auth.isAdmin())) {
        	pass = false
	}
	if(!pass) {
	    console.log(`${$location.path()} - route denied. User not logged in or authorized.`)
	    event.preventDefault()
	    $location.path('/login')        	
	}
}