
apigwProxyPipeline {
    appName = 'cdc-api'
    availability = 'public'
    httpsListener = true
    sslCertificateTest = 'arn:aws:acm:eu-west-1:438898015039:certificate/5d5e6a42-47e4-48ae-807d-07d7058b183e'
    sslCertificateStage = 'arn:aws:acm:eu-west-1:438898015039:certificate/5d5e6a42-47e4-48ae-807d-07d7058b183e'
    sslCertificateProd = 'arn:aws:acm:eu-west-1:438898015039:certificate/5d5e6a42-47e4-48ae-807d-07d7058b183e'
    desiredCountTest = 1
    desiredCountProd = 2
    businessUnit = 'ChargeAndDrive'
    buildCommand = './gradlew clean build swaggerTs'
    npmProjects = ['typescript-client']
}