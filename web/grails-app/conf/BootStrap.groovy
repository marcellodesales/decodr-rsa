import javax.servlet.ServletContext;
import grails.util.Environment

class BootStrap {

  def init = { ServletContext ctx ->

    switch (Environment.current) {

      case Environment.DEVELOPMENT:
        ctx.setAttribute("env", "dev")
        break

      case Environment.PRODUCTION:
        ctx.setAttribute("env", "prod")
        break
    }
    
    Environment.executeForCurrentEnvironment {
      production {
          // do something in production
      }
      development {
          println "Running on dev";
      }
    }
  }

  def destroy = {
    environments {
      production {
        log.debug("Terminating the server...")
      }
      development {
        log.debug("Cleaning resources...")
      }
    }
  }
}
