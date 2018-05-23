@Library('tingcore@feature/DEVOPS-568') _

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
    customResources = true
}
