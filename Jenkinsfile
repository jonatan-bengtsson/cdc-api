
applicationPipeline {
    appName = 'cdc-api'
    desiredCountTest = 1
    desiredCountProd = 2
    businessUnit = 'ChargeAndDrive'
    buildCommand = './gradlew clean build swaggerTs'
    npmProjects = ['typescript-client']

    // create apigw resources with vpc_integration
    apigwRepoName = "terraform-module-cd-api-gateway"
    apigwVpcIntegration = true
    cognitoUserPoolName = "ChargeDrive"
}
