# SenseCAP Hotspot Controller - Backend

This is a Ktor application deployed on a RaspberryPi 4, with the usage of Balena! 

More adventurous users could probably deploy it to the SenseCAP hotspot itself, but since this is too risky, we are not advising to do so!

Apart from the usage of a RaspberryPi, you can obviously deploy it on any device/server etc. that is present in the same network as your 
SenseCAP hotspot.

We created this controller in order to be able to access the local console of our SenseCAP hotspot remotely! 

In the future it could probably be extended to accept more brands, but since we only had SenseCAP hotspots we experimented with it as a starter!

## Usage

All you need to do in order to use this Ktor backend application, is to create a Docker image of it and then deploy it wherever it suits your needs! 

### Building a Docker image

In order to build a Cloud native Docker image you can do the following: 

```shell
$ cd sensecap-controller-backend //Make sure you are inside the `sensecap-controller-backend` directory first
$ ./gradlew installDist
$ docker build -t my-sensecap-controller:0.0.1 .
```

Now you will be able to find a Docker image in your local machine. Use this image in order to deploy anywhere! 

### Deploying with Balena

```shell
$ cd sensecap-controller-backend //Make sure you are inside the `sensecap-controller-backend` directory first
$ balena deploy your-org/your-device --build
```

Running the above will make use of the `docker-compose.yml` file in order to deploy a `controller` service on the
selected Balena organization and device.

## After deployment

The Ktor backend application expects 2 environment variables in order to properly work. 

* `HOTSPOT_API` - The local IP that you have reserved for your Hotspot
* `HOTSPOT_TOKEN` - The token of your SenseCAP hotspot

After you have added these 2 environment variables you need to be very careful since from now on (if you have set everything up correctly)
the backend application will be able to communicate with your hotspot!

## API Routes

* `/sensecap/shutdown`
* `/sensecap/reboot`
* `/sensecap/reset-blocks`
* `/sensecap/fast-sync`

# :warning: Disclaimer :warning:

This project is in no way affiliated to SenseCAP or Helium! Please be very careful before you use it and consider all downsides
and aspects of it before you add it, in any of your networks!
