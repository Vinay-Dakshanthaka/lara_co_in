import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-company-show',
  templateUrl: './company-show.component.html',
  styleUrls: ['./company-show.component.css']
})
export class CompanyShowComponent implements OnInit {

  constructor() { }

  slides = [
    { id: 1, image: './assets/images/Recruiters/1.jpg' },
    { id: 2, image: './assets/images/Recruiters/2.jpg' },
    { id: 3, image: './assets/images/Recruiters/3.jpg' },
    { id: 4, image: './assets/images/Recruiters/4.jpg' },
    { id: 5, image: './assets/images/Recruiters/5.jpg' },
    { id: 6, image: './assets/images/Recruiters/6.jpg' },
    { id: 7, image: './assets/images/Recruiters/7.jpg' },
    { id: 8, image: './assets/images/Recruiters/8.jpg' },
    { id: 9, image: './assets/images/Recruiters/9.jpg' },
    { id: 10, image: './assets/images/Recruiters/10.jpg' },
    { id: 11, image: './assets/images/Recruiters/11.jpg' },
    { id: 12, image: './assets/images/Recruiters/12.jpg' },
    { id: 13, image: './assets/images/Recruiters/21.jpg' },
    { id: 14, image: './assets/images/Recruiters/22.jpg' },
    { id: 15, image: './assets/images/Recruiters/23.jpg' },
    { id: 16, image: './assets/images/Recruiters/24.jpg' },
    { id: 17, image: './assets/images/Recruiters/25.jpg' },
    { id: 18, image: './assets/images/Recruiters/31.jpg' },
    { id: 19, image: './assets/images/Recruiters/32.jpg' },
    { id: 20, image: './assets/images/Recruiters/33.jpg' },
    { id: 21, image: './assets/images/Recruiters/34.jpg' },
    { id: 22, image: './assets/images/Recruiters/35.jpg' },
    { id: 23, image: './assets/images/Recruiters/36.jpg' },
  ];

  ngOnInit() { }

  chunk(arr: any[], chunkSize: number): any[][] {
    let R = [];
    for (let i = 0; i < arr.length; i += chunkSize) {
      R.push(arr.slice(i, i + chunkSize));
    }
    return R;
  }
}
