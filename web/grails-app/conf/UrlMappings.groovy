class UrlMappings {

  static mappings = {
    "/$controller/$action?/$id?"{ 
        constraints { 
          // apply constraints here
        }
    }

    "/"(controller: 'key', action: 'index')

    "500"(view:'/error')
  }
}
