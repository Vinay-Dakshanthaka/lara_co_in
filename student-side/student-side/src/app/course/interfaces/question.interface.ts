export interface Question {
    Question:[];
}

export interface Details {
    id:number;
    questionDesc: string; // required field with minimum 5 characters
    options: Options[]; // user can have one or more addresses
}

export interface Options {
    id: number;  // required field
    optionDesc: string;
}