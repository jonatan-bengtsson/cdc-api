
applicationPipeline {
    //See parameter documentation at https://bitbucket.tingcore-infra.com/projects/JEN/repos/tingcore-global-pipeline/browse/docs/pipelines/applicationPipeline.md .
    appName = 'cdc-api'
    desiredCountTest = 1
    desiredCountProd = 2
    businessUnit = 'ChargeAndDrive'
    buildCommand = './gradlew clean build swaggerTs'
    npmProjects = ['typescript-client']

    // create apigw resources with vpc_integration
    createApigw = true
    apigwVpcIntegration = true
    cognitoUserPoolName = "ChargeDrive"
    prodTimeout = 20
    memoryReservation = 2560
    cpu = 2048
}
