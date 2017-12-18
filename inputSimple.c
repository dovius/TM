int sum(int a){
return a+3;
}
int testukas(int array[],int b){
    array[1]=1;
    array[2]=1+array[1];

    return array[2];
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
 int c=testukas(array3,3); //IndexOutOfBoundsException
 write c;

    return 3;
}