export class TimeSpentOnVideo {
    coursePackageId: number;
    videoId: string;
    userId: string;  
    microTopicId: string;
    timeSpent: number;
    frequency: number;   
  
    constructor(coursePackageId: number, videoId:string, userId:string, microTopicId:string, timeSpent:number, frequency:number){
      this.coursePackageId = coursePackageId;
      this.videoId = videoId;
      this.userId = userId;
      this.microTopicId = microTopicId;
      this.timeSpent = timeSpent;
      this.frequency = frequency;
    }
  }