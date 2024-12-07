import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-data',
  templateUrl: './data.component.html',
  styleUrls: ['./data.component.css']
})
export class DataComponent implements OnInit {

  animateCount : any;
  constructor() { 

  }

  ngOnInit() {
  }
 
  
}
function animateCount(element, target, addPlusSign = true) {
  const duration = 2000; // 2 seconds
  const frames = 60; // Number of animation frames
  const increment = Math.ceil(target / frames);
  let current = 0;

  const interval = setInterval(() => {
    current += increment;
    element.textContent = current;

    if (current >= target) {
      clearInterval(interval);
      element.textContent = addPlusSign ? target + '+' : target;
    }
  }, duration / frames);
}

function startAnimation() {
  const statsElements = document.querySelectorAll('.stats-no');
  const targets = [2005, 100000, 250, 560]; // Your target values

  statsElements.forEach((element, index) => {
    animateCount(element, targets[index], index !== 0);
  });
}

document.addEventListener('DOMContentLoaded', () => {
  startAnimation();

  setInterval(startAnimation, 10000); // Re-trigger the animation every 10 seconds
});
