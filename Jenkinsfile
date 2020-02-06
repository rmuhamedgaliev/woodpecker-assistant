pipeline {
    options {
        buildDiscarder(
                logRotator(
                        artifactDaysToKeepStr: "",
                        artifactNumToKeepStr: "",
                        daysToKeepStr: "",
                        numToKeepStr: "10"
                )
        )
        disableConcurrentBuilds()
    }

    agent any

    stages {

        stage("Clone repo") {
            steps {
                checkout(
                    [
                        $class : "GitSCM",
                        branches : [
                            [
                                name: "${SELECTED_BRANCH.BRANCH_NAME}"
                            ]
                        ],
                        doGenerateSubmoduleConfigurations: false,
                        extensions: [
                            [
                                $class: "CloneOption",
                                depth: 0,
                                noTags: false,
                                reference: "",
                                shallow: false
                            ]
                        ],
                        submoduleCfg : [],
                        userRemoteConfigs : [
                            [
                                credentialsId: "citronium-rm-github",
                                url : "https://github.com/rmuhamedgaliev/woodpecker-assistant.git"
                            ]
                        ]
                    ]
                )
            }
        }

        stage("Build jar") {
            agent {
                docker {
                    image "gradle:latest"
                    args "-v ${PWD}:/usr/src/app -w /usr/src/app"
                    reuseNode true
                    label "build-image"
                }
            }
            steps {
                sh "gradle build"
            }
        }

    }

    post {
        always {
            step([$class: "WsCleanup"])
            cleanWs()
        }
    }
}
