// import { Component, OnInit } from '@angular/core';

// @Component({
//   selector: 'app-reviews',
//   templateUrl: './reviews.component.html',
//   styleUrls: ['./reviews.component.css']
// })
// export class ReviewsComponent implements OnInit {

//   constructor() { }

//   ngOnInit() {
//   }

// }


import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.css']
})
export class ReviewsComponent implements OnInit {
  testimonials = [
    {
      name: 'Vipin Raj Bishnoi',
      position: 'Software Developer At Hu-Tech Solutions',
      image: './assets/images/reviews/vipin.jfif',
      rating: 5,
      feedback: 'I had a great time doing this course and everyone involved in the LARA TECHNOLOGY has made it a great experience. I look forward to doing more courses with you guys in the future and have been recommending you to everyone I know. Thank you for all the assistance and feedback, it has been delightful and very gratifying.',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Vinayak Banavi',
      position: 'SoftWare Developer At Predictive Analytics Solutions',
      image: './assets/images/reviews/vinayak.jfif',
      rating: 5,
      feedback: 'I am immensely grateful for the support and assistance provided by Ramesh Sir and the entire team atLara Technologies. Their guidance has been instrumental in my journey towards a career in IT.',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Amit Kumar Srivastava',
      position: 'Software Developer At Cognis Solutions Pvt Ltd.',
      image: './assets/images/reviews/amit.jfif',
      rating: 5,
      feedback: 'If anyone is looking for a better place to become an IT professional, then I suggested to Lara Technologies where you learn things slowly- slowly.Here clears all the point and start from the scratch.',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Akmal Hussain',
      position: 'Sostware Developer At GENXPLM-Technologies',
      image: './assets/images/reviews/akmal.jfif',
      rating: 5,
      feedback: 'now I have enough confidence to face any interview as lara is a one stop solution for overall 360 degree development of one And along with that daily logical coding classes as well as practice are going on The sessions.',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Chandrahas G',
      position: 'Software Developer At Predictive Analytics',
      image: './assets/images/reviews/chandrahaspng.png',
      rating: 5,
      feedback: 'Lara technologies is the good place to start your career as java full stack developer. I should thank Ramesh sir for giving good guidance and encouragement. I got job within 4 months',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Chakradhar Kaluvai',
      position: 'SoftWare Developer At Reactiveworks IT Solutions',
      image: './assets/images/reviews/chakradhar.png',
      rating: 5,
      feedback: 'With help of Ramesh sir (founder of Lara) i learnt from scratch and I got placed in ReactiveWorks IT  Solutions Pvt. Ltd with in 3.5months of course duration.',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Satish Raj',
      position: 'Sostware Developer At Reactiveworks IT Solutions',
      image: './assets/images/reviews/satishjfif.jfif',
      rating: 5,
      feedback: 'Everything here becomes very easy and simple for me throughout the training period just because of Ramesh Sir because the teaching style of the sir and the subject knowledge he provided us is best in every aspects',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Umair Shaikh',
      position: 'Software Developer At Reactiveworks IT Solutions',
      image: './assets/images/reviews/umair.png',
      rating: 5,
      feedback: 'If someone looking or searching for best place to become an IT professional i highly recommended to join Lara technology where you will learn things in very proper way.</',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Gowtham Mani',
      position: 'Software Developer At Bonbloc',
      image: './assets/images/reviews/gowthan.jfif',
      rating: 5,
      feedback: 'I joined in the Lara 1st day itself I started learn java. Day by day my confidence is increasing automatically by attending all classes daily at offline. we will spend almost 15 hrs daily in lara.',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Sumanth',
      position: 'Software Developer At Treosoft',
      image: './assets/images/reviews/sumanth.jfif',
      rating: 5,
      feedback: 'I joined lara technology to upgrade my skills. Lara technology is the best place to learn Java from core to advance. Ramesh sir, his way of teaching was unmatchable, he will teach every concepts from scratch.',
      stars: [1, 2, 3, 4, 5]
    },
    {
      name: 'Surya Mudimukku',
      position: 'SoftWare Developer At Modefin',
      image: './assets/images/reviews/surya.jfif',
      rating: 5,
      feedback: 'Lara Technologies is excellent institute to build your career in IT field. I do not have that much knowledge on java before.Ramesh sir teaches every concept clearly and everyone should understarnd.',
      stars: [1, 2, 3, 4, 5]
    },
    
  ];

  visibleTestimonials = [];
  selectedTestimonial;
  currentStartIndex = 0;
  testimonialsToShow = 3;

  constructor() { }

  ngOnInit() {
    this.updateVisibleTestimonials();
    this.selectedTestimonial = this.testimonials[0];
  }

  updateVisibleTestimonials() {
    this.visibleTestimonials = this.testimonials.slice(this.currentStartIndex, this.currentStartIndex + this.testimonialsToShow);
  }

  next() {
    if (this.currentStartIndex + this.testimonialsToShow < this.testimonials.length) {
      this.currentStartIndex++;
      this.updateVisibleTestimonials();
    }
  }

  prev() {
    if (this.currentStartIndex > 0) {
      this.currentStartIndex--;
      this.updateVisibleTestimonials();
    }
  }

  selectTestimonial(testimonial) {
    this.selectedTestimonial = testimonial;
  }
}
