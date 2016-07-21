const MODULE_NAME = 'converstion_tracking'
const ADMIN_PAGE = 'hypermedia/admin.html'
const HOME_PAGE = 'hypermedia/home.html'
const LOGIN_PAGE = 'hypermedia/login.html'
const REGISTER_PAGE = 'hypermedia/register.html'
const DOMAIN_BASEDIR = 'scripts/domain'

const SPRING_LOGIN_URI = '/user/login'
const SPRING_REGISTER_URI = '/user'
const SPRING_LISTURLS_URI = '/urls'
const SPRING_LISTURLS_WITH_ANONYMOUSHITS_URI = '/urls/and/hits'
const SPRING_TRACKING_DETAILS_URI = '/urls/and/tracking'
const SPRING_NEWURL_URI = '/url'
const SPRING_FINDURL_URI = '/url/find'
const SPRING_DELETEBYLABELURL_URI = '/url/label'
const SPRING_INCREMENT_ANONYMOUS_URI = '/url/increment'
const SPRING_DECREMENT_ANONYMOUS_URI = '/url/decrement'
	
const STATES = { "All Time": undefined, "Weekly": "weekly", "Monthly": "monthly", "Yearly": "yearly" }