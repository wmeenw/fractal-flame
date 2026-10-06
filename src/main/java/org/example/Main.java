package org.example;

public class Main {
    public static void main(String[] args) {
        FlameApplication app = new FlameApplication();
        try {
            app.run(args);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

//mvn clean package
//java -jar target/fractal-flame-1.0-SNAPSHOT.jar -w 1024 -h 768 -i 5000000 -o pic1_basic.png -f swirl:1.0,horseshoe:0.8 -ap "0.5,0,0,0,0.5,0/0.5,0,0.5,0,0.5,0/0.5,0,0,0,0.5,0.5/0.5,0,0.5,0,0.5,0.5"
//java -jar target/fractal-flame-1.0-SNAPSHOT.jar -w 1024 -h 768 -i 8000000 -o pic2_sym6.png -t 2 -s 6 -f swirl:1.0,horseshoe:0.8,sinusoidal:0.6 -ap "0.5,0,0,0,0.5,0/0.5,0,0.5,0,0.5,0/0.5,0,0,0,0.5,0.5/0.5,0,0.5,0,0.5,0.5"
//java -jar target/fractal-flame-1.0-SNAPSHOT.jar -w 1280 -h 720 -i 10000000 -o pic3_heart.png -t 4 -s 4 -f swirl:1.0,horseshoe:0.8,heart:1.2 -ap "0.8,0,0,0,0.8,0/0.5,0.5,0,0.5,0.5,0/0.5,0,0.5,0,0.5,0.5"
//java -jar target/fractal-flame-1.0-SNAPSHOT.jar -w 1024 -h 1024 -i 12000000 -o pic4_horseshoe.png -t 8 -s 8 -f horseshoe:1.0 -ap "0.6,0.2,-0.2,0.2,0.6,0/0.4,-0.3,0.1,0.3,0.4,0.2"
//java -jar target/fractal-flame-1.0-SNAPSHOT.jar --config config_pic5.json
//java -jar target/fractal-flame-1.0-SNAPSHOT.jar