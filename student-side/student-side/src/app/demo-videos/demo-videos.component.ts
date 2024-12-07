import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

interface Video {
  title: string;
  url: string;
  duration: string;
}

interface Topic {
  name: string;
  videos: Video[];
  id: string;
}

@Component({
  selector: 'app-demo-videos',
  templateUrl: './demo-videos.component.html',
  styleUrls: ['./demo-videos.component.css']
})
export class DemoVideosComponent implements OnInit {

  @ViewChild('videoPlayer', { static: false }) videoPlayer!: ElementRef<HTMLVideoElement>;
  @ViewChild('loadingAnimation', { static: false }) loadingAnimation!: ElementRef<HTMLDivElement>;

  topics: Topic[] = [
    {
      name: 'Core Java',
      videos: [
        { title: 'for loop', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/mt18-for-loop-81mins.mp4', duration: '80 Min' },
        { title: 'super() Calling Statements ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/inheritance2.mp4', duration: '90 Min' }
      ],
      id: 'coreJava'
    },
    {
      name: 'Core Java Advanced',
      videos: [
        { title: 'Threads Basics', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-advanced/videos/threads-basics.mp4', duration: '160 Min' },
        { title: 'collection api (basic operations) ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-advanced/videos/collections%3Dmt2.mp4', duration: '71 Min' },
        { title: 'JDK 1.8 streams creation ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-advanced/videos/jdk1.8-streams-set1-21mins.mp4', duration: '21 Min' }
      ],
      id: 'coreJavaAdvanced'
    },
    {
      name: 'Logical Coding',
      videos: [
        { title: 'Display Patterns', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/logical-coding/display-patterns/display-patterns1.mp4', duration: '36 Min' },
        { title: 'Strings', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/logical-coding/strings/strings-2021-set3-10mins.mp4', duration: '11 Min' }
      ],
      id: 'logicalCoding'
    },
    {
      name: 'SQL',
      videos: [
        { title: 'SQL - DDL & DML', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/sql/videos/mt132-sql-with-ddl-dml.mp4', duration: '54 Min' },
        { title: 'Mapping and Joins (one-to-one', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/sql/videos/mt-136-25mins.mp4', duration: '25 Min' }
      ],
      id: 'sql'
    },
    {
      name: 'JDBC',
      videos: [
        { title: 'Setup', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/jdbc/videos/jdbc1-setup.mp4', duration: '20 Min' },
        { title: 'First Program', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/jdbc/videos/jdbc2-first_program.mp4', duration: '14 Min' },
        { title: 'Various ways of insert', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/jdbc/videos/jdbc3-insert.mp4', duration: '28 Min' }
      ],
      id: 'jdbc'
    },
    {
      name: 'Servelets',
      videos: [
        { title: 'Setup', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/servlets/videos/servlet1-setup-17mins.mp4', duration: '17 Min' },
        { title: 'First Servelet', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/servlets/videos/servlet2-first-progarm-30mins.mp4', duration: '30 Min' },
        { title: 'Play around with servlets', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/servlets/videos/servlets3-input-27mins.mp4', duration: '27 Min' }
      ],
      id: 'servelet'
    },
    {
      name: 'JSP',
      videos: [
        { title: 'Set 1', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/jsp/videos/jsp1.mp4', duration: '17 Min' },
        { title: 'Set @', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/jsp/videos/jsp2.mp4', duration: '25 Min' },
        { title: 'Set 3', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/jsp/videos/jsp3.mp4', duration: '36 Min' }
      ],
      id: 'jsp'
    },
    {
      name: 'JavaScript',
      videos: [
        { title: 'JavaScript ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/jdbc/videos/jdbc1-setup.mp4', duration: '4 Min' },
        { title: 'Output ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/javascript/videos/js-output-2.mp4', duration: '13 Min' },
        { title: 'Input ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/javascript/videos/js-input-3.mp4', duration: '10 Min' }
      ],
      id: 'javaScript'
    },
    {
      name: 'Angular',
      videos: [
        { title: 'Angular', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/angular/ang-1.mp4', duration: '21 Min' },
        { title: 'Creating a Project', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/angular/ang-3.mp4', duration: '40 Min' },
        { title: 'Creating a Component', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/angular/ang-4.mp4', duration: '18 Min' }
      ],
      id: 'angular'
    },
    {
      name: 'Spring Boot',
      videos: [
        { title: 'Spring Boot', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/spring-boot/springboot-first-app.mp4', duration: '21 Min' },
        { title: 'Spring Boot Auto Configuration', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/spring-boot/springboot-auto-configure.mp4', duration: '14 Min' },
        { title: 'Spring Boot Dev Tools', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/spring-boot/springboot-dev-tools.mp4', duration: '10 Min' }
      ],
      id: 'springBoot'
    },
    {
      name: 'Rest WebServices',
      videos: [
        { title: 'Return types', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/rest-webservices-in-springboot/restwebservices-return-values.mp4', duration: '44 Min' },
        { title: 'Path Variable', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/rest-webservices-in-springboot/restwebservice-path-variable.mp4', duration: '33 Min' },
        { title: 'Request Body', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/rest-webservices-in-springboot/rest-api-requestbody.mp4', duration: '40 Min' }
      ],
      id: 'restWebServices'
    },
    {
      name: 'Spring Data JPA',
      videos: [
        { title: 'Setup', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/spring-data-jpa/spring-data-jpa-session1-setup.mp4', duration: '48 Min' },
        { title: 'save operation', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/spring-data-jpa/data-jpa-basics-save.mp4', duration: '27 Min' },
        { title: 'saveAll Operation', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/spring-data-jpa/data-jpa-saveall.mp4', duration: '11 Min' }
      ],
      id: 'springDtaJpa'
    },
    {
      name: 'Spring Security',
      videos: [
        { title: 'Setup', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/security/spring-security-set1.mp4', duration: '19 Min' },
        { title: 'Inmemory Authentication', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/security/spring-security-set2.mp4', duration: '33 Min' },
        { title: 'Reading Credentials from DB', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/security/spring-security-set3.mp4', duration: '38 Min' }
      ],
      id: 'springSecurity'
    },
    {
      name: 'Spring Microservices',
      videos: [
        { title: 'Spring Microservices Architecture', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/micro-services/videos/spring-micro-services-intro-13mins.mp4', duration: '13 Min' },
        { title: 'Spring Microservices Setup', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/micro-services/videos/spring-microservices-setup-18mins.mp4', duration: '18 Min' },
        { title: 'Rest Template', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/micro-services/videos/spring-ms-rest-template-17mins.mp4', duration: '17 Min' }
      ],
      id: 'springMicroservices'
    },
    {
      name: 'Java FSD Project',
      videos: [
        { title: 'Step 1', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/java-fsd-pr-dev-2021/videos/pr-dev-step1-30mins.mp4', duration: '33 Min' },
        { title: 'Step 2', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/java-fsd-pr-dev-2021/videos/pr-dev-step2-25mins.mp4', duration: '25 Min' },
        { title: 'Step 3', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/java-fsd-pr-dev-2021/videos/pr-dev-step3-14mins.mp4', duration: '14 Min' }
      ],
      id: 'javaFsdProject'
    },
    {
      name: 'Python ',
      videos: [
        { title: 'Setup', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/python-core/final-python-recordedd-files/2.setup-final-py-and-pycharm.mp4', duration: '52 Min' },
        { title: 'Basics of Programming', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/python-core/final-python-recordedd-files/3.section1.mp4', duration: '25 Min' },
        { title: 'Binary Operators', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/python-core/final-python-recordedd-files/5.section2-set2.mp4', duration: '42 Min' }
      ],
      id: 'Python'
    },
    {
      name: 'Java Backup classes for late joineees  ',
      videos: [
        { title: 'JDK, winrar and editplus installations and setting a PATH', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/lara-java-setup-april-2022.mp4', duration: '13 Min' },
        { title: 'First Program', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/jdk-setup-july-2021/java-helloword-program-4mins.mp4', duration: '4 Min' },
        { title: 'Development Structure', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/mt3-development-structure.mp4', duration: '32 Min' },
        { title: 'Programming Elements ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/mt4-elements.mp4', duration: '61 Min' },
        { title: 'println and print', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/mt5-printing.mp4', duration: '27 Min' },
        { title: 'Local variables ', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/mt6-variables-86mins.mp4', duration: '86 Min' },
        { title: 'Unary Operator', url: 'https://lara.blr1.cdn.digitaloceanspaces.com/test-cb-bucket01/lara/c2c/core-java-basics/videos/mt7-unary-60min.mp4', duration: '60 Min' }
      ],
      id: 'backupClasses'
    },
  ];
  selectedTopic: Topic | null = this.topics[0];
  selectedVideo: Video | null = this.topics[0].videos[0];

  ngOnInit() {
    // Initialize selected video or any other logic needed on init
  }

  playVideo(video: Video, topic: Topic) {
    this.selectedVideo = video;
    this.selectedTopic = topic;
    if (this.videoPlayer && this.videoPlayer.nativeElement) {
      this.videoPlayer.nativeElement.load();
      this.videoPlayer.nativeElement.play(); // Autoplay selected video
    }
  }

  showLoading() {
    if (this.loadingAnimation) {
      this.loadingAnimation.nativeElement.style.display = 'block';
    }
  }

  hideLoading() {
    if (this.loadingAnimation) {
      this.loadingAnimation.nativeElement.style.display = 'none';
    }
  }
}
