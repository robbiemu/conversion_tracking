//require('./constants')

angular.module(MODULE_NAME)
    .config(['$routeProvider', function ($routeProvider) {
            $routeProvider.when('/', {
                templateUrl: HOME_PAGE,
                controller: 'HomeController',
                controllerAs: 'homeController',
                resolve: {
                    factory: function ($q, $rootScope, $location, $http, Auth) {
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
                    factory: function ($q, $rootScope, $location, $http, Auth) {
                    	loadURLs($rootScope, $http)
                    	let checkAdmin = true
                    	checkRouting($q, $rootScope, $location, Auth, checkAdmin)
                    }
                }
            })
            .when('/login', {
                templateUrl: LOGIN_PAGE,
                controller: 'UserController',
                controllerAs: 'userController'
            })
            .when('/register', {
                templateUrl: REGISTER_PAGE,
                controller: 'UserController',
                controllerAs: 'userController'
            })
            .when('/register/:extension', {
                templateUrl: REGISTER_PAGE,
                controller: 'UserController',
                controllerAs: 'userController'
            })
           .when('/login/:extension', {
                templateUrl: LOGIN_PAGE,
                controller: 'UserController',
                controllerAs: 'userController'
            })
            .otherwise('/')
        }
   ])

const loadURLs = function ($rootScope, $http) {
	$http.get(SPRING_LISTURLS_WITH_ANONYMOUSHITS_URI).then((tx_response) => {
		if(tx_response.status == 200) {
			$rootScope.URLs = []
			for(let e in tx_response.data.field){
				let url = tx_response.data.field[e].right
				url.anonymousCount = tx_response.data.field[e].left
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