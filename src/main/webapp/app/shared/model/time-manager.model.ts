export interface ITimeManager {
    id?: number;
    day?: string;
    startTime?: string;
    endTime?: string;
    restaurantId?: number;
}

export class TimeManager implements ITimeManager {
    constructor(
        public id?: number,
        public day?: string,
        public startTime?: string,
        public endTime?: string,
        public restaurantId?: number
    ) {}
}
