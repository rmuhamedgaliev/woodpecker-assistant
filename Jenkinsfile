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
                                    name: "develop"
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
                                    credentialsId: "rm-github-jenkins",
                                    url : "git@github.com:rmuhamedgaliev/woodpecker-assistant.git"
                                ]
                            ]
                        ]
                    )
                }
            }

            stage("Build jar") {
                agent {
                    docker {
                        image "gradle:jdk13"
                        args "-v ${PWD}:/usr/src/app -w /usr/src/app"
                        reuseNode true
                        label "build-image"
                    }
                }
                steps {
                    sh "mv src/main/resources/application-citronium.yaml src/main/resources/application.yaml"
                    sh "cat src/main/resources/application.yaml"
                    sh "gradle build"
                }
            }

            stage("Up on staging") {
                steps {
                    script {
                        sh "docker-compose -p wa -f docker-compose.yml up -d"
                    }
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
