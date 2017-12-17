int get(int mas[], int index) {
    for (int i=0;i<2;++i) {
        write mas[i];
    }
    return mas[index];
}
//
//int test(int a, int b) {
//    write a;
//    write b;
//    return 22;
//}

int fact(int number) {
    if (number ==0) {
        return 1;
    }
    else {
        return number * fact(number - 1);
    }
    return 0;
}

int main() {
    int mas[20];
    mas[0] = 123;
    mas[1] = 122;
    mas[2] = 121;
    int plz = get(mas,0);

    int mas2[21];
    for (int i = 0; i<21; ++i) {
        mas2[i] = i;
    }
    write get(mas2, 11);

    string zodis = 'labas';
    string sudurtinis = zodis + zodis;
    int a = 2+2;
    write a;
    write sudurtinis;


    return fact(6);
}