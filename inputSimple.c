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

int main() {
    int mas[20];
    mas[0] = 123;
    mas[1] = 122;
    mas[2] = 121;

    int plz = get(mas,0);
    return plz;
}