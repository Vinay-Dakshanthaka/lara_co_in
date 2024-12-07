import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  showTerms() {
    document.getElementById("termsOfUseButton").click();
  }

  scrollUp() {
    console.log('from scroll')
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    })
  }

}
