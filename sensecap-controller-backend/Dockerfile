FROM balenalib/raspberrypi4-64-ubuntu-openjdk:11-bionic
RUN mkdir /app
COPY ./build/install/sensecap-controller-backend/ /app/
WORKDIR /app/bin
CMD ["./sensecap-controller-backend"]
