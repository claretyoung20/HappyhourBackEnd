export interface IBookTable {
    id?: number;
    isAvaliable?: boolean;
    persons?: number;
    price?: number;
    imageUrl?: string;
    restaurantId?: number;
}

export class BookTable implements IBookTable {
    constructor(
        public id?: number,
        public isAvaliable?: boolean,
        public persons?: number,
        public price?: number,
        public imageUrl?: string,
        public restaurantId?: number
    ) {
        this.isAvaliable = this.isAvaliable || false;
    }
}
