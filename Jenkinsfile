@Library('tingcore@feature/not-fail-on-client-publishing') _

applicationPipeline {
    createApigw = true
    appName = 'cdc-api'
    desiredCountTest = 1
    desiredCountProd = 2
    businessUnit = 'ChargeAndDrive'
    buildCommand = './gradlew clean build swaggerTs'
    npmProjects = ['typescript-client']
}
