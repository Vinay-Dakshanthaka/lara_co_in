import { EventTypes } from "./event-types";

export interface ToastEvent {
    type: EventTypes,
    message: string
}