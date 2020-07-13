def defaultPipeline(region=null, awsProfile=null, applicationId=null) {
  dir('src') {
    def commitId          = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    def authorName        = sh(script: "git --no-pager show -s --format='%an' HEAD", returnStdout: true).trim()
    
    stage('Install Dependencies') {
      sh 'pwd'
      println("joewalker ${commitId}-${authorName}")
    }

    stage('Check quality') {
      sh """
        /home/dependency-check/bin/dependency-check.sh \
        --project 'spring-boot-demo' \
        --scan './src/main/kotlin'
      """
      // sh """
      //   /home/sonar-scanner/bin/sonar-scanner \
      //   -Dsonar.projectKey=spring-boot-demo \
      //   -Dsonar.sources=src/main/kotlin \
      //   -Dsonar.host.url=http://host.docker.internal:9000
      // """
    }
    stage('Unit Test') {}
    stage('Integration Test') {}
    stage('E2E Test') {}
    stage('Build & Push Image') {}
    stage('Deploy') {}

    publishHTML([
      allowMissing: false,
      alwaysLinkToLastBuild: true,
      keepAll: true,
      reportDir: "./",
      reportFiles: 'dependency-check-report.html',
      reportName: 'Dependency Check Report',
      reportTitles: ''])
    }
}

return this;