// @Library('tingcore@feature/DEVOPS-582') _

applicationPipeline {
    //See parameter documentation at https://bitbucket.tingcore-infra.com/projects/JEN/repos/tingcore-global-pipeline/browse/docs/pipelines/applicationPipeline.md .
    appName = 'cdc-api'
    desiredCountTest = 1
    desiredCountProd = 2
    businessUnit = 'ChargeAndDrive'
    buildCommand = './gradlew clean build swaggerTs'
    npmProjects = ['typescript-client']
    prodTimeout = 20
    memoryReservation = 2560
    cpu = 1024
    https = false

    customResources = true
    customLbNameTest = 'cd-test-cdc-api-nlb-int'
    customLbPortTest = 80
    customTgNameTest = 'cdc-api-test-int'
    customLbNameStage = 'cd-stage-cdc-api-nlb-int'
    customLbPortStage = 80
    customTgNameStage = 'cdc-api-stage-int'
    customLbNameProd = 'cd-prod-cdc-api-nlb-int'
    customLbPortProd = 80
    customTgNameProd = 'cdc-api-prod-int'
}
