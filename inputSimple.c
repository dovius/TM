int sum(int a){
    return a+3;
}
int testukas(int array[],int b){
    array[1]=1;
    array[2]=1+array[1];
    return array[2];
}

int fact(int number) {
    if (number ==0) {
        return 1;
    }
    else {
        return number * fact(number - 1);
    }
    return 0;
}

int get(int mas[], int index) {
    for (int i=0;i<2;++i) {
        write mas[i];
    }
    return mas[index];
}

int main(){
//*************************
    string array[20];
    array[0]='labas';
    write array[0];
//*************************
    int array2[30];
    array2[2]=2;
    int b=sum(array2[2]);
    write b;
//*************************
    int array3[30];
    int c=testukas(array3,3);
    write c;

    int mas[20];
    mas[0] = 123;
    mas[1] = 122;
    mas[2] = 121;
    int test = get(mas,0);

    int mas2[21];
    for (int i = 0; i<21; ++i) {
        mas2[i] = i;
    }
    write get(mas2, 11);
    string zodis = 'labas';
    string sudurtinis = zodis + zodis + zodis;
    int a = 2+2;
    write a;
    write sudurtinis;
    write fact(6);
    write toString(1) + 'zodis' + toString(toInt('2'));

    return 3;
}