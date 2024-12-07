export class ExamResult{
    coursePackageName: string;
    courseName: string;
    topicName: string;
    subTopicName: string;
    microTopicName: string;
    totlaMarks: number;
    marksObtained: number;
    totalQuestions: number;
    questionAnsData: string;
 
    constructor(coursePackageName: string, courseName: string, topicName: string,
        subTopicName: string, microTopicName: string, totlaMarks: number, marksObtained: number,
        totalQuestions: number, questionAnsData: string){
        this.coursePackageName = coursePackageName;
        this.courseName = courseName;
        this.topicName = topicName;
        this.subTopicName = subTopicName;
        this.microTopicName = microTopicName;
        this.totlaMarks = totlaMarks;
        this.marksObtained = marksObtained;
        this.totalQuestions = totalQuestions;
        this.questionAnsData = questionAnsData;
    }
}

