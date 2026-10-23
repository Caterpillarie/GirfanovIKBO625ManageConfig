# Эмулятор командной строки (Вариант №6)

Кроссплатформенный эмулятор терминала в форме консольного интерфейса (CLI). 
Программа работает с виртуальной файловой системой (VFS), не совершая операций в диске.

## Поддерживаемые команды:
* `ls` — просмотр содержимого текущего или указанного каталога.
* `cd` — смена текущей директории.
* `vfs-init` — замена текущей VFS на VFS по умолчанию
* `exit` — завершение работы эмулятора

## Запуск эмулятора

* **Windows:** Файл `run.bat`
* **macOS / Linux:** `./run.sh`

Напрямую через Java

```bash
java -jar shellemulator.jar --vfs test.zip --script start.txt
#--vfs - путь к ZIP-архиву (по умолчанию test.zip)
#--script - путь к текстовому файлу с командами
```

## Запуск тестов

В папке `tests` для каждого этапа есть своя папка `step{№}`, хранящая тестовые `.bat` и `.txt` с командами, 
исполняемый `.jar` и, с этапа 3, виртуальные файловые системы VFS в формате `.zip`. 

## Примеры использования

```text
Started. Type 'exit' to quit
girfa@MSI:/$ ls                      //show current directory's content
test2/
girfa@MSI:/$ cd test2                   //entering test directory
girfa@MSI:/test2$ ls                      //show next directory's content
inner_test/ surface.txt
girfa@MSI:/test2$ cd /test2/inner_test/deepest_test    //testing from root folder
girfa@MSI:/test2/inner_test/deepest_test$ ls
anotherabyss/ theabyss/ ufoundme.txt
girfa@MSI:/test2/inner_test/deepest_test$ cd test2/inner_test/deepest_test    
cd: test2/inner_test/deepest_test: No such directory
girfa@MSI:/test2/inner_test/deepest_test$ unknown_command unknown_arg1        //unknown test
unknown_command: command not found
girfa@MSI:/test2/inner_test/deepest_test$ vfs-init                //reentering default vfs
girfa@MSI:/$ ls                      //show content again
test2/
girfa@MSI:/$ ls test2/inner_test      //show content not from current folder
deepest_test/ test.bin weneedtogodeeper.txt
girfa@MSI:/$ cd test2/inner_test      //testing from current folder
girfa@MSI:/test2/inner_test$ ls
deepest_test/ test.bin weneedtogodeeper.txt
girfa@MSI:/test2/inner_test$ cd deepest_test/theabyss   //testing from curr again
girfa@MSI:/test2/inner_test/deepest_test/theabyss$ ls
dont.txt
girfa@MSI:/test2/inner_test/deepest_test/theabyss$ cd ..                   //test ..
girfa@MSI:/test2/inner_test/deepest_test$ ls
anotherabyss/ theabyss/ ufoundme.txt
girfa@MSI:/test2/inner_test/deepest_test$ Script ended
girfa@MSI:/test2/inner_test/deepest_test$ ls
anotherabyss/ theabyss/ ufoundme.txt
girfa@MSI:/test2/inner_test/deepest_test$ cd ../..
girfa@MSI:/test2$ ls
inner_test/ surface.txt
girfa@MSI:/test2$ exit
Exiting...
```
