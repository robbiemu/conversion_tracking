angular.module(MODULE_NAME).service('Res', ['$rootScope', function ($rootScope) {
	var scope = $rootScope
	scope._scripts = scope._scripts||[]
	scope._styles = scope._styles||[]
	scope._rawScripts = scope._rawScripts||[]
	scope._rawStyles = scope._rawStyles||[]
	
	return {
		style: function(inp) {
			let hrefs = []
			if(typeof inp === 'string') {
				hrefs.push(inp)
			} else {
				hrefs = inp
			}
			scope._styles=[]
			for(let i in hrefs){
				console.log("styling page with " + hrefs[i])
		  		let style = document.createElement('link')
				if (!/^https?\:\/\//.test(hrefs[i])) {
					style.type = 'text/css'
				}
				style.href = hrefs[i]
		  		style.rel = 'stylesheet'
		
				if(scope._rawStyles === undefined) {
					scope._rawStyles = []
				}
				scope._rawStyles.push([hrefs[i], style])
		  		scope._styles.push( document.head.appendChild(style) )
		
				scope.$on('$destroy', this.clean_styles)			
			}
		},
		clean_styles: function() {
			let removables = []
			for (let key in scope._rawStyles){
				console.log('deleting style ' + scope._rawStyles[key][0])
				scope._styles[key].parentNode.removeChild(scope._rawStyles[key][1])
				removables.push(key)
			}
			for(let key in removables) {
				if(scope._styles.length == 1) {
					scope._styles.pop()
				} else {
					scope._styles.splice(key,1)
				}
				if(scope._rawStyles.length == 1) {
					scope._rawStyles.pop()
				} else {
					scope._rawStyles.splice(key,1)
				}
			}
		},
		script: function(inp) {
			let hrefs = []
			if(typeof inp === 'string') {
				hrefs.push(inp)
			} else {
				hrefs = inp
			}
			scope._scripts=[]
			for(let i in hrefs){
				console.log("loading javascript: " + hrefs[i])
				let script = document.createElement('script')
				if(!/^https?\:\/\//.test(hrefs[i])) {
					script.type = 'text/javascript'					
				}
				script.src = hrefs[i]
			
				if(scope._rawScripts === undefined) {
					scope._rawScripts = []
				}
				scope._rawScripts.push([hrefs[i], script])
				scope._scripts.push( document.head.appendChild(script) )
			
				scope.$on('$destroy', this.clean_scripts)							
			}
		},
		clean_scripts: function() {
			let removables = []
			for (let key in scope._rawScripts){
				console.log('deleting script ' + scope._rawScripts[key][0])
				scope._scripts[key].parentNode.removeChild(scope._rawScripts[key][1])
				removables.push(key)
			}
			for(let key in removables) {
				if(scope._scripts.length == 1) {
					scope._scripts.pop()
				} else {
					scope._scripts.splice(key,1)
				}
				if(scope._rawScripts.length == 1) {
					scope._rawScripts.pop()
				} else {
					scope._rawScripts.splice(key,1)
				}
			}
		}
		
	}
}])