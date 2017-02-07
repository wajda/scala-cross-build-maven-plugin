@Library('libpipelines@master') _

hose {
    EMAIL = 'qa'
    SLACKTEAM = 'stratioqa'
    DEVTIMEOUT = 40
    MODULE = 'scala-cross-build-maven-plugin'
    REPOSITORY = 'scala-cross-build-maven-plugin'
    FOSS = true
    MOJO = true

    DEV = { config ->        
        doCompile(config)
        doUT(config)
        doPackage(config)
                        
        parallel(DOC: {
            doDoc(config)
        }, QC: {
            doStaticAnalysis(config)
        }, DEPLOY: {
            doDeploy(config)
        }, failFast: config.FAILFAST)
        
    }
}
