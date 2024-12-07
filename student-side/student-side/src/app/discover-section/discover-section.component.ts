import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-discover-section',
  templateUrl: './discover-section.component.html',
  styleUrls: ['./discover-section.component.css'],
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
export class DiscoverSectionComponent implements OnInit {
  counter2005: number = 0;
  counter100k: number = 0;
  counter250: number = 0;

  constructor() {}

  ngOnInit(): void {
    this.animateCounters();
  }

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
