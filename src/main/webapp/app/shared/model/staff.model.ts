export interface IStaff {
    id?: number;
    staffCode?: string;
    userLogin?: string;
    userId?: number;
    restaurantY?: string;
    restaurantId?: number;
}

export class Staff implements IStaff {
    constructor(
        public id?: number,
        public staffCode?: string,
        public userLogin?: string,
        public userId?: number,
        public restaurantY?: string,
        public restaurantId?: number
    ) {}
}
