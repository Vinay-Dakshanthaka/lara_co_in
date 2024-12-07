import { Component, OnInit } from '@angular/core';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { trigger, state, style, transition, animate } from '@angular/animations';


@Component({
  selector: 'app-hero-section-new',
  templateUrl: './hero-section-new.component.html',
  styleUrls: ['./hero-section-new.component.css'],
  animations: [
    trigger('fadeIn', [
      state('in', style({ opacity: 1, transform: 'translateY(0)' })),
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(50px)' }),
        animate('1s ease-out'),
      ]),
    ]),
  ],
})
export class HeroSectionNewComponent implements OnInit {
  textArray: string[] = "Lara Technologies".split(" ");
  isVisible: boolean = true;
  messages: string[] = [
    "Master Java Full Stack Development and advance your career.",
    "Learn Data Structures and Algorithms with real-world projects.",
    "Join the best courses to become a coding expert!"
  ];
  currentMessageIndex: number = 0;
  currentMessage: string = this.messages[this.currentMessageIndex];

  // Course Details
  courseDetails = {
    title: 'Enhance Your Skills with Our Java Fullstack Development Course',
    subtitle: 'Develop core competencies and advanced techniques in Java technologies and frameworks. Learn from the basics to advanced topics and transform yourself into a full-fledged developer.',
    duration: '4 months',
    courseHours: '750',
    prerequisites: 'No Prerequisites',
    skills1: [
      'Core Java',
      'SQL',
      'DSA',
      'JavaScript',
      'Angular',
      'Rest WebServices',
    ],
    skills2: [
      'Spring Core',
      'Spring Boot',
      'Spring Data JPA',
      'Spring Security',
      'Spring MicroServices',
    ],
  };

  // Combine skills1 and skills2 using concat() method to avoid tslib dependency issue
  combinedSkills: string[] = this.courseDetails.skills1.concat(this.courseDetails.skills2);
  currentSkillIndex: number = 0;
  currentSkill: string = this.combinedSkills[this.currentSkillIndex];

  ngOnInit(): void {
    library.add(fas);

    this.startTextAnimation();
    this.rotateMessages();
    this.rotateSkills();
    this.animateCounters();

  }

  startTextAnimation(): void {
    setInterval(() => {
      this.isVisible = !this.isVisible;
    }, 2000);
  }

  rotateMessages(): void {
    setInterval(() => {
      this.currentMessageIndex = (this.currentMessageIndex + 1) % this.messages.length;
      this.currentMessage = this.messages[this.currentMessageIndex];
    }, 3000);
  }

  rotateSkills(): void {
    setInterval(() => {
      this.currentSkillIndex = (this.currentSkillIndex + 1) % this.combinedSkills.length;
      this.currentSkill = this.combinedSkills[this.currentSkillIndex];
    }, 2000);
  }

  counter2005: number = 0;
  counter100k: number = 0;
  counter250: number = 0;

  constructor() {}

  

  animateCounters(): void {
    const duration = 2000; // 2 seconds
    const steps2005 = 2005;
    const steps100k = 100000;
    const steps250 = 250;

    const interval2005 = duration / steps2005;
    const interval100k = duration / steps100k;
    const interval250 = duration / steps250;

    let count2005 = 0;
    let count100k = 0;
    let count250 = 0;

    const timer2005 = setInterval(() => {
      count2005 += 5;
      this.counter2005 = count2005;
      if (count2005 >= steps2005) clearInterval(timer2005);
    }, interval2005);

    const timer100k = setInterval(() => {
      count100k += 100;
      this.counter100k = count100k;
      if (count100k >= steps100k) clearInterval(timer100k);
    }, interval100k);

    const timer250 = setInterval(() => {
      count250 += 1;
      this.counter250 = count250;
      if (count250 >= steps250) clearInterval(timer250);
    }, interval250);
  }

  onMouseEnter(event: MouseEvent): void {
    const card = (event.currentTarget as HTMLElement);
    card.style.transform = 'scale(1.05)';
    card.style.boxShadow = '0 15px 30px rgba(0, 0, 0, 0.2)';
  }

  onMouseLeave(event: MouseEvent): void {
    const card = (event.currentTarget as HTMLElement);
    card.style.transform = 'scale(1)';
    card.style.boxShadow = 'none';
  }
}
