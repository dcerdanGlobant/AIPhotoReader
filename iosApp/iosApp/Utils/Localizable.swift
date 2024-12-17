//
//  Localizable.swift
//  iosApp
//
//  Created by Miguel Ferrer on 13/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit

// MARK: - Localizable
protocol Localizable: CustomStringConvertible {
    var rawValue: String { get }
}

// MARK: - Localizable
extension Localizable {
    var localized: String { NSLocalizedString(rawValue, comment: "") }
    var uppercased: String { localized.uppercased() }
    var description: String { localized }
    func localized(with args: CVarArg...) -> String { String(format: localized, arguments: args) }
}

// MARK: - String
extension String {
    enum shareImage: String, Localizable {
        case title = "shareImage_title"
        case cancel = "shareImage_cancel"
        case getMoreInfo = "shareImage_getMoreInfo"
        case noOriginalImage = "shareImage_noOriginalImage"
        case noPreviewImage = "shareImage_noPreviewImage"
        case noByteArray = "shareImage_noByteArray"
        case noImageDescription = "shareImage_noImageDescription"
    }
}
