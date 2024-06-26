![DynamX](https://dynamx.fr/img/head-logo.png)

# DynamX - ImmersiveAddon

This addon adds new features to DynamX :

- Harrow
- Anti puncture
- Hands on the steering wheel
- x

### How to add modules:

To use modules ingame you have to add specific code blocks.
Here are some examples:

#### Harrow

To add this module to your prop, you need to edit the prop file inside your DynamX pack. This file have a name like block_NAME.
```
prop_NAME{
  #...
  HarrowAddon{
    IsHarrow: true
  }
}
```

#### Anti puncture

To add this module to your wheel, you need to edit the wheel file inside your DynamX pack. This file have a name like wheel_NAME.
```
HarrowAddon{
    CanFlattened: false
}
```

#### TowTruck

To add this module to your tow truck, you need to edit the car file inside your DynamX pack. This file have a name like vehicle_NAME.
```
TowTruckInfo{
    VehicleAttachPoint: 0 0 0
}
```

## Links

DynamX website: https://dynamx.fr  
Addon download: https://dynamx.fr/addons  
DynamX wiki: https://dynamx.fr/wiki/  
Discord: https://discord.gg/y53KGzD 

## Authors

* **DynamX** - *Initial work* - [DynamX](https://dynamx.fr)
* **Ertinox45** - *Project initiator* - [Ertinox45](https://github.com/Ertinox45)
* **Gabidut76** - *Developer* - [Gabidut76](https://github.com/gabidut)
* **BlackNite** - *Thanks for the radio* - [BlackNite](https://github.com/BlackNiteHD)
