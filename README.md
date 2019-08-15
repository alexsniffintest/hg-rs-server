# hg-rs-server

Hunger Games RS private server.

## Requirements

* JDK8
* SBT 1.2.8
* Docker (optional)
* Helm (optional)

## How To 

### Run Locally

To run the project locally, it's recommanded to use IntelliJ. Create a configuration to run the project. Select the project as an Applcation. The main class will be `server.Server`.

### Deployment

1. Update the `image.tag` in the [helm/hg-rs-server/values.yaml](helm/hg-rs-server/values.yaml) to the new version.
2. Deploy a new version of the server by creating a new git tag/release.
3. Go to [TravisCI](https://travis-ci.com/alexsniffintest/hg-rs-server), a build should be triggered for the new tag (only tags are built to save on cost.) Verify the docker image is pushed successfully in the final step.
4. Login to the server and start an update countdown (10 minutes is recommended so people can finish their games.)
5. Go to our [ArgoCD](https://35.237.154.238) (ask for credentials.)
6. Find the application `prod` and then click sync.
7. Check that the old pod terminates and a new one is started under the StatefulSet.
8. Verify start up logs on the pod look correct and that no missing files or exceptions are thrown, e.g:
    ```
    [8/12/19 6:31 PM]: Launching RuneScape Hunger Games...
    [8/12/19 6:31 PM]: [ObjectDef] DONE LOADING OBJECT CONFIGURATION
    [8/12/19 6:31 PM]: [Region] DONE LOADING REGION CONFIGURATIONS
    [8/12/19 6:31 PM]: Server Statistics loaded.
    [8/12/19 6:31 PM]: Server listening on port 0.0.0.0:46900
    ```
9. Verify you can login to the server.

### Java + Scala

This project supports both Java and Scala. SBT (scala build tool) can be used to compile both source codes. Be wary that there are minor API differences when interchanging the two.

### Creating Jar

You can manually create a jar file by using SBT.

```
sbt clean assembly
```

This will save under the `/target/...` directory.
