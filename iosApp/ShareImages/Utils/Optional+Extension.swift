//
//  Optional+Extension.swift
//  iosApp
//
//  Created by Miguel Ferrer on 5/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

// MARK: - Optional
extension Optional {
    var isNil: Bool {
        self == nil
    }
    
    var isNotNil: Bool {
        self != nil
    }
}
