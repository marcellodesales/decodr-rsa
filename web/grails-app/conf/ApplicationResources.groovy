modules = {
    application {
        resource url:'js/application.js'

    }

    prettify {
      resource url:'css/prettify.css'
      resource url:'js/prettify/prettify.js'
    }

    jqueryclipboard {
      resource url:'js/jquery/jquery.zclip.js', disposition:'head'
    }
}